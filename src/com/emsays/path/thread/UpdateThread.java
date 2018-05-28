package com.emsays.path.thread;

import com.emsays.path.APIHelper;
import com.emsays.path.GV;
import com.emsays.path.Util;
import com.emsays.path.dao.app.UpdateLogSer;
import com.emsays.path.dto.api.UpdateDataDTO;
import com.emsays.path.dto.api.UpdateResDTO;
import com.emsays.path.dto.app.UpdateLogDTO;
import org.hibernate.Session;

public class UpdateThread extends Thread
{

	Session session;

	public UpdateThread(Session session)
	{
		this.session = session;
	}

	@Override
	public void run()
	{

		UpdateLogSer updateLogSer;
		updateLogSer = new UpdateLogSer(session);

		boolean run = true;

		while (run)
		{

			try
			{
				if (Util.mIsNetConnected())
				{
					UpdateResDTO updateResDTO = new APIHelper(
						"", "", "", GV.APP_TYPE_ID, "", updateLogSer.getLatestInstalledVersion().getVersion(), "")
						.getNewUpdates();

					if (updateResDTO.getData().size() > 0)
					{

						updateLogSer = new UpdateLogSer(session);

						for (UpdateDataDTO updateDataDTO : updateResDTO.getData())
						{
							UpdateLogDTO updateLogDTO = new UpdateLogDTO();
							updateLogDTO.setVersion(updateDataDTO.getVersion());
							updateLogDTO.setPath(updateDataDTO.getFile_path());
							updateLogDTO.setStatusId(1);
							updateLogSer.createOrUpdate(updateLogDTO);
						}
					}

					run = false;

				}
				else
				{
					Thread.sleep(10000);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				run = false;
			}

		}

	}

}
