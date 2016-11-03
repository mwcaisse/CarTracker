package com.ricex.cartracker.androidrequester.request.type;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.common.viewmodel.EntityResponse;

public class BulkUploadResponseType extends ParameterizedTypeReference<EntityResponse<List<BulkUploadResult>>> {

}
