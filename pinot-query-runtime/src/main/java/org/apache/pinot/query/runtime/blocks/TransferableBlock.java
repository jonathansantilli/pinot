/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pinot.query.runtime.blocks;

import java.io.IOException;
import org.apache.pinot.core.common.Block;
import org.apache.pinot.core.common.BlockDocIdSet;
import org.apache.pinot.core.common.BlockDocIdValueSet;
import org.apache.pinot.core.common.BlockMetadata;
import org.apache.pinot.core.common.BlockValSet;
import org.apache.pinot.core.common.datablock.BaseDataBlock;
import org.apache.pinot.core.common.datablock.ColumnarDataBlock;
import org.apache.pinot.core.common.datablock.RowDataBlock;


/**
 * A {@code TransferableBlock} is a wrapper around {@link BaseDataBlock} for transferring data using
 * {@link org.apache.pinot.common.proto.Mailbox}.
 */
public class TransferableBlock implements Block {

  private BaseDataBlock _dataBlock;
  private BaseDataBlock.Type _type;

  public TransferableBlock(BaseDataBlock dataBlock) {
    _dataBlock = dataBlock;
    _type = dataBlock instanceof ColumnarDataBlock ? BaseDataBlock.Type.COLUMNAR
        : dataBlock instanceof RowDataBlock ? BaseDataBlock.Type.ROW : BaseDataBlock.Type.METADATA;
  }

  public BaseDataBlock getDataBlock() {
    return _dataBlock;
  }

  public BaseDataBlock.Type getType() {
    return _type;
  }

  public byte[] toBytes()
      throws IOException {
    return _dataBlock.toBytes();
  }

  @Override
  public BlockValSet getBlockValueSet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BlockDocIdValueSet getBlockDocIdValueSet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BlockDocIdSet getBlockDocIdSet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public BlockMetadata getMetadata() {
    throw new UnsupportedOperationException();
  }
}
