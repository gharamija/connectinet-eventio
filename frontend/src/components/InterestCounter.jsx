import * as React from "react";
import Badge from "@mui/material/Badge";

export default function InterestCounter({ interest, children }) {
  return (
    <Badge badgeContent={interest} color="primary" sx={{ maxWidth: 500 }}>
      {children}
    </Badge>
  );
}
