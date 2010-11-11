package org.sonatype.sitemap.io;

/**
 * Marks an object that is has MIME type available.
 * 
 * @author cstamas
 */
public interface MimeTyped
{
    /**
     * Returns the MIME type as string.
     * 
     * @return
     */
    String getMimeType();
}
