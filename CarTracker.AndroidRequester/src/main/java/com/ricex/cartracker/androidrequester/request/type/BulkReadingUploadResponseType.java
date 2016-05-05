package com.ricex.cartracker.androidrequester.request.type;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.ReadingUploadResult;

public class BulkReadingUploadResponseType extends ParameterizedTypeReference<EntityResponse<List<ReadingUploadResult>>> {

}
