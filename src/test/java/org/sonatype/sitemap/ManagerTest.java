package org.sonatype.sitemap;

import java.io.File;

import junit.framework.TestCase;

import org.sonatype.sitemap.attributes.DummyContributor;
import org.sonatype.sitemap.io.FileContent;
import org.sonatype.sitemap.record.Key;
import org.sonatype.sitemap.record.Path;
import org.sonatype.sitemap.record.Record;
import org.sonatype.sitemap.record.StringKey;
import org.sonatype.sitemap.space4j.Space4jBackend;
import org.space4j.Space4J;
import org.space4j.implementation.SimpleLogger;
import org.space4j.implementation.SimpleSpace4J;

public class ManagerTest
    extends TestCase
{
    protected Space4J space4j;

    protected Manager manager;

    protected void setUp()
        throws Exception
    {
        super.setUp();

        SimpleLogger.setDir( "target/space4j_db" );

        space4j = new SimpleSpace4J( "Sitemap", 1000L );

        space4j.executeSnapshot();

        this.manager = new DefaultManager( new Space4jBackend( space4j ) );

        this.manager.registerContributor( new DummyContributor() );
    }

    protected void tearDown()
        throws Exception
    {
        super.tearDown();

        space4j.executeSnapshot();
    }

    protected void dumpRecord( Record cr )
    {
        System.out.println( cr.toString() );

        System.out.println( cr.getAttributes().toString() );
    }

    public void testSimple()
    {
        Key alpha = new StringKey( "alpha" );

        assertTrue( manager.createSitemap( alpha ) );

        Sitemap map = manager.getSitemap( alpha );

        map.put( new Path( "/some/path" ), new FileContent( new File( "pom.xml" ) ) );

        map.put( new Path( "/some/other/path" ), new FileContent( new File( "pom.xml" ) ) );

        for ( int i = 0; i < 10; i++ )
        {
            Path path = new Path( "/some/path/" + i );
            map.put( path, new FileContent( new File( "pom.xml" ) ) );
        }

        dumpRecord( map.get( new Path( "/some/path" ) ) );
        dumpRecord( map.get( new Path( "/some/other/path" ) ) );
    }

}
