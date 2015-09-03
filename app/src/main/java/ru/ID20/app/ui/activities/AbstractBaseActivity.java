package ru.ID20.app.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import ru.ID20.app.R;
import ru.ID20.app.constants.enums.TransactionOperation;
import ru.ID20.app.tools.Tools;
import ru.ID20.app.ui.fragments.AbstractFragment;

/**
 * Created by s.shevchenko on 06.02.2015.
 */
public abstract class AbstractBaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected ScheduledExecutorService executorService;
    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        handler = new Handler();
        executorService = Executors.newScheduledThreadPool(3);
        initUI();
    }

    abstract void initUI();

    abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        Tools.shutdownAndAwaitTermination(executorService);
        super.onDestroy();
    }

    public Handler getHandler() {
        return handler;
    }


    public void showFragment(AbstractFragment fragment, boolean addInBackstack, boolean clearStack, boolean withAnimation) {
        showFragment(fragment, addInBackstack, clearStack, TransactionOperation.REPLACE, withAnimation);
    }

    public void showFragment(AbstractFragment fragment, boolean addInBackstack, boolean clearStack, TransactionOperation operation, boolean withAnimation) {

        if (fragment != null) {
            if (fragment.isResumed()) {
                return;
            }
            if (clearStack) {
                while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (withAnimation) {
           //     transaction.setCustomAnimations(R.anim.enter, R.anim.exit);
            }

            switch (operation) {
                case ADD:
                    transaction.add(R.id.container, fragment, ((Object) fragment).getClass().getSimpleName());
                    break;
                case REPLACE:
                    transaction.replace(R.id.container, fragment, ((Object) fragment).getClass().getSimpleName());
                    break;
            }

            if (addInBackstack) {
                transaction.addToBackStack(((Object) fragment).getClass().getSimpleName());
            }
            transaction.commit();
        }
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }
}