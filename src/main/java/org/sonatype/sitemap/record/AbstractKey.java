package org.sonatype.sitemap.record;

import java.io.Serializable;

@SuppressWarnings( "serial" )
public abstract class AbstractKey
    implements Key, Serializable
{
    @Override
    public abstract String stringValue();

    @Override
    public int hashCode()
    {
        return stringValue().hashCode();
    }

    @Override
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

    @Override
    public String toString()
    {
        return stringValue() + " (" + getClass().getName() + ")";
    }
}
