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
package org.apache.pinot.query.planner.stage;

import org.apache.pinot.common.proto.Plan;


public final class StageNodeSerDeUtils {
  private StageNodeSerDeUtils() {
    // do not instantiate.
  }

  public static AbstractStageNode deserializeStageNode(Plan.StageNode protoNode) {
    AbstractStageNode stageNode = newNodeInstance(protoNode.getNodeName(), protoNode.getStageId());
    stageNode.fromObjectField(protoNode.getObjectField());
    for (Plan.StageNode protoChild : protoNode.getInputsList()) {
      stageNode.addInput(deserializeStageNode(protoChild));
    }
    return stageNode;
  }

  public static Plan.StageNode serializeStageNode(AbstractStageNode stageNode) {
    Plan.StageNode.Builder builder = Plan.StageNode.newBuilder()
        .setStageId(stageNode.getStageId())
        .setNodeName(stageNode.getClass().getSimpleName())
        .setObjectField(stageNode.toObjectField());
    for (StageNode childNode : stageNode.getInputs()) {
      builder.addInputs(serializeStageNode((AbstractStageNode) childNode));
    }
    return builder.build();
  }

  private static AbstractStageNode newNodeInstance(String nodeName, int stageId) {
    switch (nodeName) {
      case "TableScanNode":
        return new TableScanNode(stageId);
      case "JoinNode":
        return new JoinNode(stageId);
      case "ProjectNode":
        return new ProjectNode(stageId);
      case "FilterNode":
        return new FilterNode(stageId);
      case "MailboxSendNode":
        return new MailboxSendNode(stageId);
      case "MailboxReceiveNode":
        return new MailboxReceiveNode(stageId);
      default:
        throw new IllegalArgumentException("Unknown node name: " + nodeName);
    }
  }
}
