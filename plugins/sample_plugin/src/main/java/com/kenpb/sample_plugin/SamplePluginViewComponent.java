package com.kenpb.sample_plugin;

import de.tschuehly.spring.viewcomponent.core.component.ViewComponent;
import de.tschuehly.spring.viewcomponent.thymeleaf.ViewContext;

@ViewComponent
public class SamplePluginViewComponent {

    public record SamplePluginView() implements ViewContext {
    }

    public SamplePluginView render() {
        return new SamplePluginView();
    }

}
