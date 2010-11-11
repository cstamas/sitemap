package org.sonatype.sitemap.record;

public class LongKey
    extends AbstractKey
{
    private static final long serialVersionUID = -1761512577636870023L;

    private final long value;

    public LongKey( final long value )
    {
        this.value = value;
    }

    public long getValue()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Long.valueOf( getValue() ).hashCode();
    }
    
    @Override
    public String toString()
    {
        return Long.toString( getValue() );
    }
}
