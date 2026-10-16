/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.fluss.client.table.writer;

import org.apache.fluss.annotation.PublicEvolving;
import org.apache.fluss.exception.FlussRuntimeException;
import org.apache.fluss.row.InternalRow;

import java.util.concurrent.CompletableFuture;

/**
 * The writer to write data to the primary key table.
 *
 * @since 0.2
 */
@PublicEvolving
public interface UpsertWriter extends TableWriter {

    /**
     * Inserts a record into Fluss table if it does not already exist, or updates it if it does.
     *
     * @param record the record to upsert.
     * @return A {@link CompletableFuture} that always returns upsert result when complete normally.
     */
    CompletableFuture<UpsertResult> upsert(InternalRow record);

    /**
     * Upsert a record in the specified bucket of its target table or partition.
     *
     * <p>For tables with bucket keys, the bucket must match the bucket determined by the record's
     * bucket key. This preserves routing for lookups and subsequent writes.
     *
     * <p>This call may block while resolving the target table or partition's bucket metadata.
     *
     * @param record the record to upsert
     * @param bucketId the zero-based target bucket id, less than the target bucket count
     * @return a future with the write result
     * @throws IllegalArgumentException if the bucket id is negative
     * @throws FlussRuntimeException if the bucket id is out of range or does not match the bucket
     *     key
     */
    CompletableFuture<UpsertResult> upsert(InternalRow record, int bucketId);

    /**
     * Delete a certain record from the Fluss table. The input must contain the primary key fields.
     *
     * @param record the record to delete.
     * @return A {@link CompletableFuture} that always delete result when complete normally.
     */
    CompletableFuture<DeleteResult> delete(InternalRow record);

    /**
     * Delete a record in the specified bucket of its target table or partition.
     *
     * <p>For tables with bucket keys, the bucket must match the bucket determined by the record's
     * bucket key. This preserves routing for lookups and subsequent writes.
     *
     * <p>This call may block while resolving the target table or partition's bucket metadata.
     *
     * @param record the record to delete
     * @param bucketId the zero-based target bucket id, less than the target bucket count
     * @return a future with the write result
     * @throws IllegalArgumentException if the bucket id is negative
     * @throws FlussRuntimeException if the bucket id is out of range or does not match the bucket
     *     key
     */
    CompletableFuture<DeleteResult> delete(InternalRow record, int bucketId);
}
