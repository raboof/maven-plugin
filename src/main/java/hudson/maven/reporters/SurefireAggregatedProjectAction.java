package hudson.maven.reporters;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.test.AbstractTestResultAction;
import hudson.tasks.test.TestResultProjectAction;

public class SurefireAggregatedProjectAction extends TestResultProjectAction {
    public SurefireAggregatedProjectAction(AbstractProject<?, ?> project) {
        super(project);
    }

    @Override
    public String getUrlName() {
        return "surefireAggregatedTest";
    }

    @Override
    public AbstractTestResultAction getLastTestResultAction() {
        final AbstractBuild<?,?> tb = project.getLastSuccessfulBuild();

        AbstractBuild<?,?> b=project.getLastBuild();
        while(b!=null) {
            SurefireAggregatedReport a = b.getAction(SurefireAggregatedReport.class);
            if(a!=null && (!b.isBuilding())) return a;
            if(b==tb)
                // if even the last successful build didn't produce the test result,
                // that means we just don't have any tests configured.
                return null;
            b = b.getPreviousBuild();
        }

        return null;
    }
}
