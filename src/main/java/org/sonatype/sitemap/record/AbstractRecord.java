package org.sonatype.sitemap.record;

@SuppressWarnings( "serial" )
public abstract class AbstractRecord
    implements Record
{
    private final Key key;

    public AbstractRecord( final Key key )
    {
        this.key = key;
    }

    public Key getKey()
    {
        return key;
    }

    public int hashCode()
    {
        return getKey().hashCode();
    }

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
