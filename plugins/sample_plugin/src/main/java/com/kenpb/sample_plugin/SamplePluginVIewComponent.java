package com.kenpb.sample_plugin;

import de.tschuehly.spring.viewcomponent.core.component.ViewComponent;
import de.tschuehly.spring.viewcomponent.jte.ViewContext;

@ViewComponent
public class SamplePluginVIewComponent {

    public record AppView() implements ViewContext {
    }

    public AppView render() {
        return new AppView();
    }

}
