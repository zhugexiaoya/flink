/*
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

package org.apache.flink.table.planner.operations;

import org.apache.flink.table.catalog.ObjectIdentifier;
import org.apache.flink.table.operations.CallProcedureOperation;
import org.apache.flink.table.operations.Operation;
import org.apache.flink.table.operations.OperationUtils;
import org.apache.flink.table.procedures.Procedure;
import org.apache.flink.table.types.DataType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Wrapper for valid call procedure operation generated by Planner. */
public class PlannerCallProcedureOperation implements CallProcedureOperation {

    private final ObjectIdentifier procedureIdentifier;
    private final Procedure procedure;

    /** The internal represent for input arguments. */
    private final Object[] internalInputArguments;

    private final DataType[] inputTypes;
    private final DataType outputType;

    public PlannerCallProcedureOperation(
            ObjectIdentifier procedureIdentifier,
            Procedure procedure,
            Object[] internalInputArguments,
            DataType[] inputTypes,
            DataType outputType) {
        this.procedureIdentifier = procedureIdentifier;
        this.procedure = procedure;
        this.internalInputArguments = internalInputArguments;
        this.inputTypes = inputTypes;
        this.outputType = outputType;
    }

    @Override
    public String asSummaryString() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("procedureIdentifier", procedureIdentifier);
        params.put("inputTypes", inputTypes);
        params.put("outputTypes", outputType);
        params.put("arguments", internalInputArguments);
        return OperationUtils.formatWithChildren(
                "CALL PROCEDURE", params, Collections.emptyList(), Operation::asSummaryString);
    }
}
