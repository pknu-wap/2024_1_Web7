import React from "react";
import "./Modal.css";

function Modal({ isOpen, closeModal, children }) {
  return (
    <>
      {isOpen && (
        <div className={`modal-overlay`}>
          <>{children}</>
          <button className="close-btn" onClick={closeModal}>
            X
          </button>
        </div>
      )}
    </>
  );
}

export default Modal;
