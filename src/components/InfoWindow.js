import React from "react";
import "./InfoWindow.css";

function InfoWindow(props) {
  return (
    <div className="info-window">
      <div className="info-window-title">{props.title}</div>
      <div className="info-window-content">{props.content}</div>
      <div className="info-window-image">{props.image}</div>
    </div>
  );
}

export default InfoWindow;
