/*
 * Space4J http://www.space4j.org/
 * Copyright (C) 2007  Sergio Oliveira Jr. (sergio.oliveira.jr@gmail.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.sonatype.sitemap.space4j;

import org.space4j.Command;
import org.space4j.CommandException;
import org.space4j.Space;

@SuppressWarnings( "serial" )
public class IncrementLongSeqCmd
    extends Command
{
    private Object target;

    private long sequence = -1;

    public IncrementLongSeqCmd( Object target )
    {
        this.target = target;
    }

    public void setSequence( long x )
    {
        this.sequence = x;
    }

    public long getSequence()
    {
        return sequence;
    }

    public int execute( Space space )
        throws CommandException
    {
        if ( sequence > 0 )
        {
            return 1;
        }

        Object obj = space.get( target );

        if ( obj == null )
        {
            throw new CommandException( "Sequence " + target.toString() + " does not exists!" );
        }

        if ( !( obj instanceof long[] ) )
        {
            throw new CommandException( target.toString() + " is not a sequence!" );
        }

        long[] seq = (long[]) obj;

        seq[0]++;

        return 1;
    }

}
