package ru.wkn.views;

import lombok.AllArgsConstructor;
import ru.wkn.exceptions.WindowTypeException;

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

    public IWindow addWindow(WindowType windowType) throws WindowTypeException {
        IWindow window = windowFactory.createWindow(windowType);
        windows.put(windowType, window);
        return window;
    }

    public IWindow getWindow(WindowType windowType) {
        return windows.get(windowType);
    }

    public void deleteWindow(WindowType windowType) {
        windows.remove(windowType);
    }
}
