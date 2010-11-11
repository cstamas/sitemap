package org.sonatype.sitemap.record;

public class CombinationalKey
    extends AbstractKey
{
    private static final long serialVersionUID = 8878583343466745264L;

    private final Key keyA;

    private final Key keyB;

    public CombinationalKey( final Key keyA, final Key keyB )
    {
        this.keyA = keyA;

        this.keyB = keyB;
    }

    public Key getKeyA()
    {
        return keyA;
    }

    public Key getKeyB()
    {
        return keyB;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( keyA == null ) ? 0 : keyA.hashCode() );
        result = prime * result + ( ( keyB == null ) ? 0 : keyB.hashCode() );
        return result;
    }

    @Override
    public String toString()
    {
        return "(a=" + getKeyA().toString() + ";b=" + getKeyB().toString() + ")";
    }
}
