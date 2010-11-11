package org.sonatype.sitemap.io;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil2;
import eu.medsea.mimeutil.detector.ExtensionMimeDetector;

/**
 * Utility class to handle MIME Type detection.
 * 
 * @author cstamas
 */
public class MimeUtils
{
    private static MimeUtil2 mimeUtil = new MimeUtil2();

    static
    {
        mimeUtil.registerMimeDetector( ExtensionMimeDetector.class.getName() );
    }

    public static String getMimeType( String fileName )
    {
        return MimeUtil2.getMostSpecificMimeType( mimeUtil.getMimeTypes( fileName ) ).toString();
    }

    public static String getMimeType( File file )
    {
        return MimeUtil2.getMostSpecificMimeType( mimeUtil.getMimeTypes( file ) ).toString();
    }

    public static String getMimeType( URL url )
    {
        return MimeUtil2.getMostSpecificMimeType( mimeUtil.getMimeTypes( url ) ).toString();
    }

    @SuppressWarnings( "unchecked" )
    public static Set<String> getMimeTypes( String fileName )
    {
        return toStringSet( mimeUtil.getMimeTypes( fileName ) );
    }

    @SuppressWarnings( "unchecked" )
    public static Set<String> getMimeTypes( File file )
    {
        return toStringSet( mimeUtil.getMimeTypes( file ) );
    }

    @SuppressWarnings( "unchecked" )
    public static Set<String> getMimeTypes( URL url )
    {
        return toStringSet( mimeUtil.getMimeTypes( url ) );
    }

    private static Set<String> toStringSet( Collection<MimeType> mimeTypes )
    {
        Set<String> result = new HashSet<String>();
        for ( MimeType mimeType : mimeTypes )
        {
            result.add( mimeType.toString() );
        }
        return result;
    }
}
