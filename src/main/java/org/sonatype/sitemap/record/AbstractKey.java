package org.sonatype.sitemap.record;

@SuppressWarnings( "serial" )
public abstract class AbstractKey
    implements Key
{
    public abstract int hashCode();

    public boolean equals( Object obj )
    {
        if ( obj == null )
        {
            return false;
        }

        if ( this == obj )
        {
            return true;
        }

        if ( getClass() == obj.getClass() )
        {
            return hashCode() == obj.hashCode();
        }
        else
        {
            return false;
        }
    }
}
