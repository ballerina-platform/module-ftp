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

package org.ballerinalang.ftp.client.endpoint;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.ftp.util.FtpConstants;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;

import java.util.Map;

import static org.ballerinalang.ftp.util.FtpConstants.FTP_PACKAGE_NAME;

/**
 * Get the client endpoint.
 *
 * @since 0.966
 */

@BallerinaFunction(
        orgName = "wso2",
        packageName = "ftp",
        functionName = "getCallerActions",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "Client", structPackage = FTP_PACKAGE_NAME),
        returnType = {@ReturnType(type = TypeKind.STRUCT)},
        isPublic = true
)
public class GetCallerActions extends BlockingNativeCallableUnit {

    @Override
    public void execute(Context context) {
        BStruct clientEndpoint = (BStruct) context.getRefArgument(0);
        BStruct clientEndpointConfig = (BStruct) clientEndpoint.getRefField(0);
        BStruct clientConnector = BLangConnectorSPIUtil
                .createBStruct(context.getProgramFile(), FTP_PACKAGE_NAME, "ClientActions",
                        clientEndpointConfig);
        final String url = (String) clientEndpoint.getNativeData(FtpConstants.URL);
        clientConnector.addNativeData(FtpConstants.URL, url);
        Map<String, String> prop = (Map<String, String>) clientEndpoint.getNativeData(FtpConstants.PROPERTY_MAP);
        clientConnector.addNativeData(FtpConstants.PROPERTY_MAP, prop);
        context.setReturnValues(clientConnector);
    }
}
