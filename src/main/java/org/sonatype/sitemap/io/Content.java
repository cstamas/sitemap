package org.sonatype.sitemap.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * The most basic content abstraction.
 * 
 * @author cstamas
 */
public interface Content
{
    /**
     * Returns the content stream. It is the caller duty to close the stream! If this content is not reusable, this
     * method might be called only once!
     * 
     * @return
     * @throws IOException
     */
    InputStream getContent()
        throws IOException;

    /**
     * Returns true if this content is reusable. For example, FS backed content might be reusable (just reopen the
     * file), while other content, like HTTP request body, might not be reusable.
     * 
     * @return
     */
    boolean isReusable();

    /**
     * Returns the content length in bytes.
     * 
     * @return
     */
    long getLength();

    /**
     * Returns the last modified time stamp of the content.
     * 
     * @return
     */
    long getLastModified();
}
