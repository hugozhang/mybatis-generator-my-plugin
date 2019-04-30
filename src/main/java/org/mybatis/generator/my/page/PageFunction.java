package org.mybatis.generator.my.page;

import java.util.Collection;

public interface PageFunction<Out> {

    <In> int ofTotal(In in);
    
    <In> Collection<Out> ofResults(In in);
    
}
