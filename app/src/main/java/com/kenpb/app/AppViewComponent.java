package com.kenpb.app;

import de.tschuehly.spring.viewcomponent.core.component.ViewComponent;
import de.tschuehly.spring.viewcomponent.thymeleaf.ViewContext;

@ViewComponent
public class AppViewComponent {

    public record AppView() implements ViewContext {
    }

    public AppView render() {
        return new AppView();
    }
}
