package ru.wkn.views;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class WindowRepository {

    private static Map<WindowType, IWindow> windows;
    private IWindowFactory windowFactory;

    static {
        windows = new HashMap<>();
    }

    public boolean isExists(WindowType windowType) {
        return windows.containsKey(windowType);
    }

    public IWindow addWindow(WindowType windowType) {
        return windows.put(windowType, windowFactory.createWindow(windowType));
    }

    public void deleteWindow(WindowType windowType) {
        windows.remove(windowType);
    }
}
