docker pull $1/$2:latest
docker run -p 5900:5900 -e DISPLAY=:99 $2
cd ~/noVNC/
./utils/novnc_proxy --vnc localhost:5900