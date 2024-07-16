package org.example.recordvideoservice.daoimpl;

import org.example.recordvideoservice.entity.RecordVideo;
import org.example.recordvideoservice.repository.RecordVideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordVideoDaoImplTest {

    @Mock
    private RecordVideoRepository mockRecordVideoRepository;

    @InjectMocks
    private RecordVideoDaoImpl recordVideoDaoImplUnderTest;

    @Test
    void testSaveRecordVideo() {
        // Setup
        final RecordVideo recordVideo = new RecordVideo();
        recordVideo.setRecordVideoID(0);
        recordVideo.setRecordVideoName("recordVideoName");
        recordVideo.setRecordVideoIntro("recordVideoIntro");
        recordVideo.setRecordVideoCoverUrl("recordVideoCoverUrl");
        recordVideo.setRecordVideoAddress("recordVideoAddress");

        // Run the test
        recordVideoDaoImplUnderTest.saveRecordVideo(recordVideo);

        // Verify the results
        // Confirm RecordVideoRepository.save(...).
        final RecordVideo entity = new RecordVideo();
        entity.setRecordVideoID(0);
        entity.setRecordVideoName("recordVideoName");
        entity.setRecordVideoIntro("recordVideoIntro");
        entity.setRecordVideoCoverUrl("recordVideoCoverUrl");
        entity.setRecordVideoAddress("recordVideoAddress");
        verify(mockRecordVideoRepository).save(entity);
    }

    @Test

    public void testDeleteByRecordVideoID() {
        // Setup
        final Integer recordVideoID = 1;

        // Run the test
        recordVideoDaoImplUnderTest.deleteByRecordVideoID(recordVideoID);

        // Verify the results
        verify(mockRecordVideoRepository).deleteByRecordVideoID(recordVideoID);
    }

    @Test
    void testFindByUserid() {
        // Setup
        final RecordVideo recordVideo = new RecordVideo();
        recordVideo.setRecordVideoID(0);
        recordVideo.setRecordVideoName("recordVideoName");
        recordVideo.setRecordVideoIntro("recordVideoIntro");
        recordVideo.setRecordVideoCoverUrl("recordVideoCoverUrl");
        recordVideo.setRecordVideoAddress("recordVideoAddress");
        final List<RecordVideo> expectedResult = List.of(recordVideo);

        // Configure RecordVideoRepository.findByUserid(...).
        final RecordVideo recordVideo1 = new RecordVideo();
        recordVideo1.setRecordVideoID(0);
        recordVideo1.setRecordVideoName("recordVideoName");
        recordVideo1.setRecordVideoIntro("recordVideoIntro");
        recordVideo1.setRecordVideoCoverUrl("recordVideoCoverUrl");
        recordVideo1.setRecordVideoAddress("recordVideoAddress");
        final List<RecordVideo> recordVideos = List.of(recordVideo1);
        when(mockRecordVideoRepository.findByUserid(0)).thenReturn(recordVideos);

        // Run the test
        final List<RecordVideo> result = recordVideoDaoImplUnderTest.findByUserid(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindByUserid_RecordVideoRepositoryReturnsNoItems() {
        // Setup
        when(mockRecordVideoRepository.findByUserid(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<RecordVideo> result = recordVideoDaoImplUnderTest.findByUserid(0);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindRecordVideoByRecordVideoID() {
        // Setup
        final RecordVideo expectedResult = new RecordVideo();
        expectedResult.setRecordVideoID(0);
        expectedResult.setRecordVideoName("recordVideoName");
        expectedResult.setRecordVideoIntro("recordVideoIntro");
        expectedResult.setRecordVideoCoverUrl("recordVideoCoverUrl");
        expectedResult.setRecordVideoAddress("recordVideoAddress");

        // Configure RecordVideoRepository.findRecordVideoByRecordVideoID(...).
        final RecordVideo recordVideo = new RecordVideo();
        recordVideo.setRecordVideoID(0);
        recordVideo.setRecordVideoName("recordVideoName");
        recordVideo.setRecordVideoIntro("recordVideoIntro");
        recordVideo.setRecordVideoCoverUrl("recordVideoCoverUrl");
        recordVideo.setRecordVideoAddress("recordVideoAddress");
        when(mockRecordVideoRepository.findRecordVideoByRecordVideoID(0)).thenReturn(recordVideo);

        // Run the test
        final RecordVideo result = recordVideoDaoImplUnderTest.findRecordVideoByRecordVideoID(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
