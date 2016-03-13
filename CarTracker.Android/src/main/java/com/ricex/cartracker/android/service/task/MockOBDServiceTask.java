package com.ricex.cartracker.android.service.task;

/**
 * Created by Mitchell on 2/13/2016.
 */
public class MockOBDServiceTask extends ServiceTask {

    public MockOBDServiceTask() {
        super(15);
    }

    @Override
    public boolean performLoopInitialization() {
        return true;
    }

    @Override
    public boolean performLoopLogic() {
        return true;
    }

    @Override
    public void performLoopFinilization() {

    }


}
