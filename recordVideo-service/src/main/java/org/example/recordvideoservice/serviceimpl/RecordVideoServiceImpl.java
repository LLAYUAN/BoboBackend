package org.example.recordvideoservice.serviceimpl;

import jakarta.transaction.Transactional;
import org.example.recordvideoservice.dao.RecordVideoDao;
import org.example.recordvideoservice.entity.RecordVideo;
import org.example.recordvideoservice.service.RecordVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordVideoServiceImpl implements RecordVideoService {
    @Autowired
    private RecordVideoDao recordVideoDao;

    @Transactional
    @Override
    public void saveRecordVideo(RecordVideo recordVideo) {
        recordVideoDao.saveRecordVideo(recordVideo);
    }

    @Transactional
    @Override
    public void deleteByRecordVideoID(Integer recordVideoID) {recordVideoDao.deleteByRecordVideoID(recordVideoID);}

    @Override
    public List<RecordVideo> findByUserid(Integer userID) {
        return recordVideoDao.findByUserid(userID);
    }

    public RecordVideo findRecordVideoByRecordVideoID(Integer recordVideoID) {
        return recordVideoDao.findRecordVideoByRecordVideoID(recordVideoID);
    }
}
