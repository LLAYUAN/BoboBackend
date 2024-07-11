package org.example.recordvideoservice.daoimpl;

import jakarta.transaction.Transactional;
import org.example.recordvideoservice.dao.RecordVideoDao;
import org.example.recordvideoservice.entity.RecordVideo;

import org.example.recordvideoservice.repository.RecordVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecordVideoDaoImpl implements RecordVideoDao {
    @Autowired
    private RecordVideoRepository recordVideoRepository;

    @Transactional
    @Override
    public void saveRecordVideo(RecordVideo recordVideo) {
        recordVideoRepository.save(recordVideo);
    }

    @Override
    public List<RecordVideo> findByUserid(Integer userID) {
        return recordVideoRepository.findByUserid(userID);
    }

    public RecordVideo findRecordVideoByRecordVideoID(Integer recordVideoID) {
        return recordVideoRepository.findRecordVideoByRecordVideoID(recordVideoID);
    }
}
