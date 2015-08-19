/*
 * @(#)WebCfgTest.java		Created at 15/7/29
 * 
 * Copyright (c) azolla.org All rights reserved.
 * Azolla PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package org.azolla.w.leon.cfg;

import org.junit.Assert;
import org.junit.Test;

/**
 * The coder is very lazy, nothing to write for this class
 *
 * @author sk@azolla.org
 * @since ADK1.0
 */
public class WebCfgTest
{
    public static  String                     REGULAR_CFG_FILENAME = "^(.)*\\.Cfg.Azolla\\.(properties|xml)$";
    @Test
    public void testRegular()
    {
        Assert.assertTrue("img.oss.roc.Cfg.Azolla.properties".matches(REGULAR_CFG_FILENAME));
        Assert.assertTrue("#.oss.roc.Cfg.Azolla.properties".matches(REGULAR_CFG_FILENAME));
        Assert.assertFalse("img.oss.roc.Cfg.azolla.properties".matches(REGULAR_CFG_FILENAME));
        Assert.assertFalse("#.oss.roc.Cfg.azolla.properties".matches(REGULAR_CFG_FILENAME));
        Assert.assertTrue("test.obj.roc.Cfg.Azolla.xml".matches(REGULAR_CFG_FILENAME));
        Assert.assertTrue("#.obj.roc.Cfg.Azolla.xml".matches(REGULAR_CFG_FILENAME));
        Assert.assertFalse("test.obj.roc.Cfg.azolla.xml".matches(REGULAR_CFG_FILENAME));
        Assert.assertFalse("#.obj.roc.Cfg.azolla.xml".matches(REGULAR_CFG_FILENAME));
    }
}
