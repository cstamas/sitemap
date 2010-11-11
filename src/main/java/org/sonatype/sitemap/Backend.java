package org.sonatype.sitemap;

import java.util.Collection;
import java.util.Map;

import org.sonatype.sitemap.record.Key;
import org.sonatype.sitemap.record.Keyed;

/**
 * A generic "backend" abstraction, that performs actual IO. It is tech independent, so it might be some plain Java
 * class doing the work, some framework or even an SQL.
 * 
 * @author cstamas
 */
public interface Backend
{
    /**
     * Returns the new Sequence value.
     * 
     * @param key
     * @return
     */
    long getNextId( Key key );

    // ==

    /**
     * Return true if map associated with key exists.
     * 
     * @param key
     * @return true if map exists.
     */
    boolean hasMap( Key key );

    /**
     * Creates a map for given key.
     * 
     * @param key
     * @return true, if map was created, false otherwise (because it was existing for example).
     */
    boolean createMap( Key key );

    /**
     * Returns the map associated to given key.
     * 
     * @param key
     * @return the map if exists
     */
    Map<Key, Keyed> getMap( Key key );

    /**
     * Removes map associated with key.
     * 
     * @param key
     * @return true, if map existed and was removed.
     */
    boolean removeMap( Key key );

    // ==

    /**
     * Puts a record cr keyed with cr.getKey() into map associated with key sid.
     * 
     * @param key the key to select the map associated with it.
     * @param keyed the record to put in, keyed with cr.getKey()
     * @return true when put was succesful
     */
    boolean put( Key sid, Key key, Object keyed );

    // put all into sit map under record.getKey()
    <T extends Key, V extends Object> int putAll( Key sid, Map<T, V> records );

    // remove from sid map the key
    boolean remove( Key sid, Key key );

    <T extends Key> int removeAll( Key sid, Collection<T> keys );
}
