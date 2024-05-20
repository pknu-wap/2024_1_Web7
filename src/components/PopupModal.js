import React from "react";
import "./PopupModal.css";

function PopupModal({ isOpen, closeModal, children }) {
  return (
    <div className="Popupmodal-overlay" onClick={closeModal}>
      {isOpen && (
        <div className={`Popup-modal`}>
          <>{children}</>
        </div>
      )}
    </div>
  );
}

export default PopupModal;
