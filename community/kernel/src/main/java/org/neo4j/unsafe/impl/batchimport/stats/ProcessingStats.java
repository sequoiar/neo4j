/**
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.unsafe.impl.batchimport.stats;

import org.neo4j.unsafe.impl.batchimport.staging.Step;
import org.neo4j.unsafe.impl.batchimport.stats.Stats.LongBasedStat;

import static org.neo4j.unsafe.impl.batchimport.stats.Stats.longStat;

/**
 * Provides common {@link Stat statistics} about a {@link Step}, stats like number of processed batches,
 * processing time a.s.o.
 */
public class ProcessingStats extends GenericStatsProvider
{
    public ProcessingStats( long receivedBatches, long doneBatches, long totalProcessingTime, long upstreamIdleTime,
            long downstreamIdleTime )
    {
        add( Keys.received_batches, longStat( receivedBatches ) );
        add( Keys.done_batches, longStat( doneBatches ) );
        add( Keys.total_processing_time, longStat( totalProcessingTime ) );
        add( Keys.upstream_idle_time, longStat( upstreamIdleTime ) );
        add( Keys.downstream_idle_time, longStat( downstreamIdleTime ) );
        add( Keys.avg_processing_time, new LongBasedStat()
        {
            @Override
            public long asLong()
            {
                long batches = stat( Keys.done_batches ).asLong();
                return batches == 0 ? 0 : stat( Keys.total_processing_time ).asLong() / batches;
            }
        } );
    }
}
