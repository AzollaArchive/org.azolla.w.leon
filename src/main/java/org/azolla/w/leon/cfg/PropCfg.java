/*
 * @(#)PropCfg.java		Created at 15/8/1
 * 
 * Copyright (c) azolla.org All rights reserved.
 * Azolla PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package org.azolla.w.leon.cfg;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.azolla.l.ling.io.Close0;
import org.azolla.l.ling.util.KV;
import org.azolla.l.ling.util.Log0;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * The coder is very lazy, nothing to write for this class
 *
 * @author sk@azolla.org
 * @since ADK1.0
 */
public class PropCfg implements WebCfg
{
    private String                       fileName        = null;
    private Properties                   prop            = new Properties();
    private LoadingCache<String, String> cfgCacheBuilder = CacheBuilder.newBuilder().build(new CacheLoader<String, String>()
    {
        @Override
        public String load(@Nonnull String key) throws Exception
        {
            return prop.getProperty(key);
        }
    });

    private PropCfg(String fileName)
    {
        this.fileName = fileName;
        refresh();
    }

    public static PropCfg cfg(String fileName)
    {
        return new PropCfg(fileName);
    }

    public String get(String key)
    {
        try
        {
            return cfgCacheBuilder.get(key);
        }
        catch (Exception e)
        {
            Log0.error(this.getClass(), e.toString(), e);
            throw new RuntimeException("Can't find {" + key + "} !");
        }
    }

    public void refresh()
    {
        refresh(fileName);
        try
        {
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            try
            {
                prop = new Properties();
                fileReader = new FileReader(cfgFileCacheBuilder.get(fileName));
                bufferedReader = new BufferedReader(fileReader);
                prop.load(bufferedReader);
            }
            catch (Exception e)
            {
                Log0.error(this.getClass(), e.toString(), e);
                throw new RuntimeException(e.toString());
            }
            finally
            {
                Close0.close(bufferedReader);
                Close0.close(fileReader);
            }
        }
        catch (Exception e)
        {
            Log0.error(PropCfg.class, KV.ins("fileName", fileName).toString(), e);
            throw new RuntimeException("Can't find {" + fileName + "} !");
        }
        cfgCacheBuilder.cleanUp();
    }
}
