package com.emsays.path.service.api;

import com.emsays.path.dto.api.UpdaterResDTO;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UpdaterSer
{

	@FormUrlEncoded
	@POST("get_new_versions/updater")
	Call<UpdaterResDTO> getNewUpdater(
		@Field("key_1") String key1, // client key
		@Field("key_2") String key2, //agent key
		@Field("key_3") String key3, //instance key
		@Field("key_4") String key4, // app type id
		@Field("name") String name, // customer name
		@Field("version") String version, // installed_version_app
		@Field("version_updater") String versionUpdater // installed_version_app
	);
}
