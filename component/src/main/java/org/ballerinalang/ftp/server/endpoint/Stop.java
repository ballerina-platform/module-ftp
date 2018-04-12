/*
 * Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.ftp.server.endpoint;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.connector.api.BallerinaConnectorException;
import org.ballerinalang.connector.api.Struct;
import org.ballerinalang.ftp.util.ServerConstants;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.wso2.transport.remotefilesystem.exception.RemoteFileSystemConnectorException;
import org.wso2.transport.remotefilesystem.server.connector.contract.RemoteFileSystemServerConnector;

import static org.ballerinalang.ftp.util.ServerConstants.FTP_PACKAGE_NAME;

/**
 * Stop the server connector.
 */

@BallerinaFunction(
        orgName = "wso2",
        packageName = "ftp",
        functionName = "stop",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "Listener", structPackage = FTP_PACKAGE_NAME),
        isPublic = true
)
public class Stop extends BlockingNativeCallableUnit {

    @Override
    public void execute(Context context) {
        Struct serviceEndpoint = BLangConnectorSPIUtil.getConnectorEndpointStruct(context);
        RemoteFileSystemServerConnector serverConnector = (RemoteFileSystemServerConnector) serviceEndpoint
                .getNativeData(ServerConstants.FTP_SERVER_CONNECTOR);
        try {
            serverConnector.stop();
        } catch (RemoteFileSystemConnectorException e) {
            throw new BallerinaConnectorException("Unable to stop server connector", e);
        }
        context.setReturnValues();
    }
}
