/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.yarn.server.api.protocolrecords.impl.pb;


import org.apache.hadoop.yarn.api.records.ProtoBase;
import org.apache.hadoop.yarn.proto.YarnServerCommonProtos.MasterKeyProto;
import org.apache.hadoop.yarn.proto.YarnServerCommonProtos.NodeActionProto;
import org.apache.hadoop.yarn.proto.YarnServerCommonServiceProtos.RegisterNodeManagerResponseProto;
import org.apache.hadoop.yarn.proto.YarnServerCommonServiceProtos.RegisterNodeManagerResponseProtoOrBuilder;
import org.apache.hadoop.yarn.server.api.protocolrecords.RegisterNodeManagerResponse;
import org.apache.hadoop.yarn.server.api.records.MasterKey;
import org.apache.hadoop.yarn.server.api.records.NodeAction;
import org.apache.hadoop.yarn.server.api.records.impl.pb.MasterKeyPBImpl;


    
public class RegisterNodeManagerResponsePBImpl extends ProtoBase<RegisterNodeManagerResponseProto> implements RegisterNodeManagerResponse {
  RegisterNodeManagerResponseProto proto = RegisterNodeManagerResponseProto.getDefaultInstance();
  RegisterNodeManagerResponseProto.Builder builder = null;
  boolean viaProto = false;
  
  private MasterKey masterKey = null;
  
  private boolean rebuild = false;
  
  public RegisterNodeManagerResponsePBImpl() {
    builder = RegisterNodeManagerResponseProto.newBuilder();
  }

  public RegisterNodeManagerResponsePBImpl(RegisterNodeManagerResponseProto proto) {
    this.proto = proto;
    viaProto = true;
  }
  
  public RegisterNodeManagerResponseProto getProto() {
    if (rebuild)
      mergeLocalToProto();
    proto = viaProto ? proto : builder.build();
    viaProto = true;
    return proto;
  }

  private void mergeLocalToBuilder() {
    if (this.masterKey != null) {
      builder.setMasterKey(convertToProtoFormat(this.masterKey));
    }
  }

  private void mergeLocalToProto() {
    if (viaProto) 
      maybeInitBuilder();
    mergeLocalToBuilder();
    proto = builder.build();
    rebuild = false;
    viaProto = true;
  }

  private void maybeInitBuilder() {
    if (viaProto || builder == null) {
      builder = RegisterNodeManagerResponseProto.newBuilder(proto);
    }
    viaProto = false;
  }

  @Override
  public MasterKey getMasterKey() {
    RegisterNodeManagerResponseProtoOrBuilder p = viaProto ? proto : builder;
    if (this.masterKey != null) {
      return this.masterKey;
    }
    if (!p.hasMasterKey()) {
      return null;
    }
    this.masterKey = convertFromProtoFormat(p.getMasterKey());
    return this.masterKey;
  }

  @Override
  public void setMasterKey(MasterKey masterKey) {
    maybeInitBuilder();
    if (masterKey == null)
      builder.clearMasterKey();
    this.masterKey = masterKey;
  }

  @Override
  public NodeAction getNodeAction() {
    RegisterNodeManagerResponseProtoOrBuilder p = viaProto ? proto : builder;
    if(!p.hasNodeAction()) {
      return null;
    }
    return convertFromProtoFormat(p.getNodeAction());
  }

  @Override
  public void setNodeAction(NodeAction nodeAction) {
    maybeInitBuilder();
    if (nodeAction == null) {
      builder.clearNodeAction();
      return;
    }
    builder.setNodeAction(convertToProtoFormat(nodeAction));
  }

  private NodeAction convertFromProtoFormat(NodeActionProto p) {
    return  NodeAction.valueOf(p.name());
  }

  private NodeActionProto convertToProtoFormat(NodeAction t) {
    return NodeActionProto.valueOf(t.name());
  }

  private MasterKeyPBImpl convertFromProtoFormat(MasterKeyProto p) {
    return new MasterKeyPBImpl(p);
  }

  private MasterKeyProto convertToProtoFormat(MasterKey t) {
    return ((MasterKeyPBImpl)t).getProto();
  }
}  
