package org.sonatype.sitemap.record;

public class CombinationalKey
    extends AbstractKey
{
    private static final long serialVersionUID = 8878583343466745264L;

    private final Key keyA;

    private final Key keyB;

    public CombinationalKey( final Key keyA, final Key keyB )
    {
        assert keyA != null : "KeyA cannot be null!";
        assert keyB != null : "KeyB cannot be null!";

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
    public String stringValue()
    {
        return getKeyA().toString() + ";" + getKeyB().toString();
    }
}
