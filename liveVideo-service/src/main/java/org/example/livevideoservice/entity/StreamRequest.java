package org.example.livevideoservice.entity;

public class StreamRequest {
    private String inputFile;
    private String rtmpUrl;
    private String cameraDevice;
    private String localFilePath;
    private String roomId;

    // Getters and setters

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getCameraDevice() {
        return cameraDevice;
    }

    public void setCameraDevice(String cameraDevice) {
        this.cameraDevice = cameraDevice;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getRoomId() {
        return roomId;
    }
}
