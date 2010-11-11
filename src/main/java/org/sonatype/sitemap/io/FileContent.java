package org.sonatype.sitemap.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is a File backed Content implementation, that is also Hashed and MimeTyped. If those are set (because they are
 * already available from some other source), the set values will be used, otherwise this class will provide the values
 * on the fly (at performance costs).
 * 
 * @author cstamas
 */
public class FileContent
    implements Content, Sha1Hashed, MimeTyped
{
    private final File file;

    private String sha1Hash;

    private String mimeType;

    public FileContent( final File file )
    {
        this.file = file;
    }

    public File getFile()
    {
        return file;
    }

    public InputStream getContent()
        throws IOException
    {
        return new FileInputStream( getFile() );
    }

    public boolean isReusable()
    {
        return true;
    }

    public long getLength()
    {
        return getFile().length();
    }

    public long getLastModified()
    {
        return getFile().lastModified();
    }

    // ==

    public String getSha1Hash()
    {
        if ( sha1Hash == null )
        {
            // calculate hash
            sha1Hash = DigesterUtils.getSha1Digest( getFile() );
        }

        return sha1Hash;
    }

    public void setSha1Hash( String sha1Hash )
    {
        this.sha1Hash = sha1Hash;
    }

    public String getMimeType()
    {
        if ( mimeType == null )
        {
            // figure out mimeType
            mimeType = MimeUtils.getMimeType( getFile() );
        }

        return mimeType;
    }

    public void setMimeType( String mimeType )
    {
        this.mimeType = mimeType;
    }
}
