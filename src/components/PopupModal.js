import React from "react";
import "./PopupModal.css";

function PopupModal({ isOpen, closeModal, children }) {
  return (
    <>
      {isOpen && (
        <div className={`Popup-modal`}>
          <>{children}</>
          <button className="close-btn" onClick={closeModal}>
            X
          </button>
        </div>
      )}
    </>
  );
}

export default PopupModal;
