package org.mybatis.generator.my.page;

import java.util.Collection;

public interface PageFunction<Out> {

    int ofTotal();
    
    Collection<Out> ofResults();
    
}
