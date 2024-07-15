package org.example.recordvideoservice.serviceimpl;

import org.example.recordvideoservice.dao.RecordVideoDao;
import org.example.recordvideoservice.entity.RecordVideo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordVideoServiceImplTest {

    @Mock
    private RecordVideoDao mockRecordVideoDao;

    @InjectMocks
    private RecordVideoServiceImpl recordVideoServiceImplUnderTest;

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
        recordVideoServiceImplUnderTest.saveRecordVideo(recordVideo);

        // Verify the results
        // Confirm RecordVideoDao.saveRecordVideo(...).
        final RecordVideo recordVideo1 = new RecordVideo();
        recordVideo1.setRecordVideoID(0);
        recordVideo1.setRecordVideoName("recordVideoName");
        recordVideo1.setRecordVideoIntro("recordVideoIntro");
        recordVideo1.setRecordVideoCoverUrl("recordVideoCoverUrl");
        recordVideo1.setRecordVideoAddress("recordVideoAddress");
        verify(mockRecordVideoDao).saveRecordVideo(recordVideo1);
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

        // Configure RecordVideoDao.findByUserid(...).
        final RecordVideo recordVideo1 = new RecordVideo();
        recordVideo1.setRecordVideoID(0);
        recordVideo1.setRecordVideoName("recordVideoName");
        recordVideo1.setRecordVideoIntro("recordVideoIntro");
        recordVideo1.setRecordVideoCoverUrl("recordVideoCoverUrl");
        recordVideo1.setRecordVideoAddress("recordVideoAddress");
        final List<RecordVideo> recordVideos = List.of(recordVideo1);
        when(mockRecordVideoDao.findByUserid(0)).thenReturn(recordVideos);

        // Run the test
        final List<RecordVideo> result = recordVideoServiceImplUnderTest.findByUserid(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindByUserid_RecordVideoDaoReturnsNoItems() {
        // Setup
        when(mockRecordVideoDao.findByUserid(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<RecordVideo> result = recordVideoServiceImplUnderTest.findByUserid(0);

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

        // Configure RecordVideoDao.findRecordVideoByRecordVideoID(...).
        final RecordVideo recordVideo = new RecordVideo();
        recordVideo.setRecordVideoID(0);
        recordVideo.setRecordVideoName("recordVideoName");
        recordVideo.setRecordVideoIntro("recordVideoIntro");
        recordVideo.setRecordVideoCoverUrl("recordVideoCoverUrl");
        recordVideo.setRecordVideoAddress("recordVideoAddress");
        when(mockRecordVideoDao.findRecordVideoByRecordVideoID(0)).thenReturn(recordVideo);

        // Run the test
        final RecordVideo result = recordVideoServiceImplUnderTest.findRecordVideoByRecordVideoID(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
