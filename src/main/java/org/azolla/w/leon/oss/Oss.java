/*
 * @(#)AliOss.java		Created at 15/7/31
 * 
 * Copyright (c) azolla.org All rights reserved.
 * Azolla PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package org.azolla.w.leon.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.base.Strings;
import org.azolla.l.ling.io.Close0;
import org.azolla.l.ling.util.Log0;
import org.azolla.w.leon.cfg.PropCfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * The coder is very lazy, nothing to write for this class
 *
 * @author sk@azolla.org
 * @since ADK1.0
 */
public enum Oss
{
    Ali
            {
                public String putObject(File file, String folder)
                {
                    String rtnString = null;
                    InputStream content = null;
                    String key = Strings.nullToEmpty(folder) + file.getName();

                    try
                    {
                        OSSClient client = new OSSClient(propCfg.get(OSS_ALI_END_POINT), propCfg.get(OSS_ALI_ACCESS_KEY_ID), propCfg.get(OSS_ALI_ACCESS_KEY_SECRET));
                        content = new FileInputStream(file);
                        ObjectMetadata meta = new ObjectMetadata();
                        meta.setContentLength(file.length());
                        PutObjectResult result = client.putObject(propCfg.get(OSS_ALI_BUCKET_NAME), key, content, meta);
                        result.getETag();
                        rtnString = getOssDomain() + key;
                    }
                    catch (Exception e)
                    {
                        Log0.error(this.getClass(), e.toString(), e);
                    }
                    finally
                    {
                        Close0.close(content);
                    }

                    return rtnString;

                }

                public String getOssDomain()
                {
                    return propCfg.get(OSS_ALI_DOMAIN_NAME);
                }
            };

    private static final String ALI_OSS_CFG_FILENAME = "alioss.w.Azolla.Cfg.properties";

    private static final String OSS_ALI_ACCESS_KEY_ID     = "OSS_ALI_ACCESS_KEY_ID";
    private static final String OSS_ALI_ACCESS_KEY_SECRET = "OSS_ALI_ACCESS_KEY_SECRET";
    private static final String OSS_ALI_END_POINT         = "OSS_ALI_END_POINT";

    private static final String OSS_ALI_BUCKET_NAME        = "OSS_ALI_BUCKET_NAME";
    private static final String OSS_ALI_MAX_CONNECTIONS    = "OSS_ALI_MAX_CONNECTIONS";
    private static final String OSS_ALI_CONNECTION_TIMEOUT = "OSS_ALI_CONNECTION_TIMEOUT";
    private static final String OSS_ALI_MAX_ERROR_RETRY    = "OSS_ALI_MAX_ERROR_RETRY";
    private static final String OSS_ALI_SOCKET_TIMEOUT     = "OSS_ALI_SOCKET_TIMEOUT";

    private static final String OSS_ALI_DOMAIN_NAME = "OSS_ALI_DOMAIN_NAME";

    PropCfg propCfg = PropCfg.cfg(ALI_OSS_CFG_FILENAME);

    /**
     * @param file
     * @param folder a/b/
     * @return
     */
    public abstract String putObject(File file, String folder);

    public abstract String getOssDomain();

}
