package org.azolla.w.leon.cfg;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.azolla.l.ling.io.File0;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;

/**
 * The coder is very lazy, nothing to write for this class
 *
 * @author sk@azolla.org
 * @since ADK1.0
 */
public interface WebCfg
{
    //String                     REGULAR_CFG_FILENAME = "^(.)*\\.Azolla.Cfg\\.(properties|xml)$";
    String                     REGULAR_CFG_FILENAME = "^(.)*\\.Azolla\\.(properties|xml)$";
    LoadingCache<String, File> cfgFileCacheBuilder  = CacheBuilder.newBuilder().build(new CacheLoader<String, File>()
    {
        @Override
        public File load(@Nonnull String cfgFilename) throws Exception
        {
            List<File> rtnList = File0.allFile(getWebINF(), (File pathname) -> pathname.getName().matches(REGULAR_CFG_FILENAME) && cfgFilename.equals(pathname.getName()));
            if (rtnList.size() > 1)
            {
                throw new Exception("Get more then one file with {" + cfgFilename + "} error.");
            }
            else
            {
                return rtnList.get(0);
            }
        }
    });

    static File getWebINF()
    {
        String thisClassPath = WebCfg.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (thisClassPath.indexOf("WEB-INF") <= 0)
        {
            throw new RuntimeException("Get web root path error.");
        }
        return File0.newFile(thisClassPath.substring(0, thisClassPath.indexOf("WEB-INF") + 8));
    }

    default void refresh(String cfgFilename)
    {
        cfgFileCacheBuilder.refresh(cfgFilename);
    }

    public void refresh();
}
