import React, { useState } from "react";
import { Box, Button, Container, Typography, Collapse, Alert, Grid } from "@mui/material";
import { Input as BaseInput } from '@mui/base/Input';
import { styled } from '@mui/system';
import UnfoldMoreRoundedIcon from '@mui/icons-material/UnfoldMoreRounded';
import PropTypes from 'prop-types';
import { Select as BaseSelect, selectClasses } from '@mui/base/Select';
import { useContext } from "react";
import { IdContext } from "../App.jsx";
import { useNavigate } from "react-router-dom";
import PayPalImage from "./paypal.png";

// Renders errors or successfull transactions on the screen.
function Message({ content }) {
    return <p>{content}</p>;
}
const CustomInput = React.forwardRef(function CustomInput(props, ref) {
    return <BaseInput slots={{ input: InputElement }} {...props} ref={ref} />;
});


function Subscriptions() {
    const initialOptions = {
        "client-id": "test",
        "enable-funding": "",
        "disable-funding": "paylater,venmo,card",
        "data-sdk-integration-source": "integrationbuilder_sc",
    };
    const [message, setMessage] = useState("");
    const id = useContext(IdContext);
    const [error, setError] = React.useState("");
    const navigate = useNavigate();
    const [cardNumber, setCardNumber] = useState("");
    const [ccv, setCcv] = useState("");
    const [cardHolderName, setCardHolderName] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [isPayPalDisabled, setIsPayPalDisabled] = useState(true);

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        checkPayPalAvailability();
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
        checkPayPalAvailability();
    };

    const handleCardNumberChange = (e) => {
        setCardNumber(e.target.value);
        checkButtonAvailability();
    };

    const handleCcvChange = (e) => {
        setCcv(e.target.value);
        checkButtonAvailability();
    };

    const handleCardHolderNameChange = (e) => {
        setCardHolderName(e.target.value);
        checkButtonAvailability();
    };

    const handlePostalCodeChange = (e) => {
        setPostalCode(e.target.value);
        checkButtonAvailability();
    };

    const checkButtonAvailability = () => {
        const areAllFieldsFilled = cardNumber && ccv && cardHolderName && postalCode;
        setIsButtonDisabled(!areAllFieldsFilled);
    };

    const checkPayPalAvailability = () => {
        const areAllFieldsFilledPP = email && password;
        setIsPayPalDisabled(!areAllFieldsFilledPP);
    };


    const povratakNaProfil = () => {
        navigate("/profil");
    };
    const handlePayPal = () => {
        setError("");
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: `id=${id}`,
        };
        fetch(`/api/transakcija/paypal/${id}`, options).then((response) => {
            if (response.status === 200) {
                setError(response.statusText);
            } else {
                setError(response.statusText);
            }
        });
    };

    const pretplati = () => {
        setError("");
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: `id=${id}`,
        };
        fetch(`/api/transakcija/banka/${id}`, options).then((response) => {
            if (response.status === 200) {
                setError(response.statusText);

            } else {
                setError(response.statusText);
            }
        });
    };

    return (
        <>
            <Box sx={{ marginBottom: 15 }}>
                <Container maxWidth="sm" sx={{ mb: 2 }}>
                    <Grid container spacing={1} alignItems="center" >
                        <Typography variant="h3" mb={1} marginBottom={5}>
                            Upravitelj pretplatama
                        </Typography>


                        <Typography variant="h5" mb={1} style={{ margin: '3px' }} >
                            Plati putem PayPal-a
                        </Typography>
                        <CustomInput aria-label="Demo input" placeholder="Email" onChange={handleEmailChange} style={{ margin: '3px' }} />
                        <CustomInput aria-label="Demo input" placeholder="Lozinka" type="password" onChange={handlePasswordChange} style={{ margin: '3px' }} />
                        <Button
                            variant="contained"
                            size="large"
                            fullWidth
                            onClick={handlePayPal}
                            style={{ backgroundColor: '#FCBB32', margin: '3px' }}
                            disabled={isPayPalDisabled}
                        >
                            <img src={PayPalImage} alt="Icon" style={{ width: 'auto', height: '23px' }} />
                        </Button>
                        <Typography variant="h5" mb={1} style={{ margin: '3px' }}>
                            ili kartičnim plaćanjem
                        </Typography>
                        <CustomInput aria-label="Demo input" placeholder="Broj kartice" onChange={handleCardNumberChange} style={{ margin: '3px' }} />
                        <CustomInput aria-label="Demo input" placeholder="CCV" onChange={handleCcvChange} style={{ margin: '3px' }} />
                        <CustomInput aria-label="Demo input" placeholder="Ime na kartici" onChange={handleCardHolderNameChange} style={{ margin: '3px' }} />
                        <CustomInput aria-label="Demo input" placeholder="Poštanski broj" onChange={handlePostalCodeChange} style={{ margin: '3px' }} />
                        <Button
                            variant="contained"
                            size="large"
                            fullWidth
                            onClick={pretplati}
                            disabled={isButtonDisabled}
                            style={{ margin: '3px' }}
                        >
                            Pretplati se
                        </Button>
                        <Button
                            variant="contained"
                            size="large"
                            fullWidth
                            onClick={povratakNaProfil}
                            style={{ margin: '3px' }}
                        >
                            Povratak na profil
                        </Button>
                        <Collapse in={error !== ""}>
                            <Alert severity="error">{error}</Alert>
                        </Collapse>
                    </Grid>
                </Container>
            </Box>
        </>
    );
}

export default Subscriptions;

const blue = {
    100: '#DAECFF',
    200: '#80BFFF',
    400: '#3399FF',
    500: '#007FFF',
    600: '#0072E5',
};

const grey = {
    50: '#F3F6F9',
    100: '#E5EAF2',
    200: '#DAE2ED',
    300: '#C7D0DD',
    400: '#B0B8C4',
    500: '#9DA8B7',
    600: '#6B7A90',
    700: '#434D5B',
    800: '#303740',
    900: '#1C2025',
};


const InputElement = styled('input')(
    ({ theme }) => `
    width: 320px;
    font-family: 'IBM Plex Sans', sans-serif;
    font-size: 0.875rem;
    font-weight: 400;
    line-height: 1.5;
    padding: 8px 12px;
    border-radius: 8px;
    color: ${theme.palette.mode === 'dark' ? grey[300] : grey[900]};
    background: ${theme.palette.mode === 'dark' ? grey[900] : '#fff'};
    border: 1px solid ${theme.palette.mode === 'dark' ? grey[700] : grey[200]};
    box-shadow: 0px 2px 2px ${theme.palette.mode === 'dark' ? grey[900] : grey[50]};
  
    &:hover {
      border-color: ${blue[400]};
    }
  
    &:focus {
      border-color: ${blue[400]};
      box-shadow: 0 0 0 3px ${theme.palette.mode === 'dark' ? blue[600] : blue[200]};
    }
  
    // firefox
    &:focus-visible {
      outline: 0;
    }
  `,
);

const CustomButton = React.forwardRef(function CustomButton(props, ref) {
    const { ownerState, ...other } = props;
    return (
        <StyledButton type="button" {...other} ref={ref}>
            {other.children}
            <UnfoldMoreRoundedIcon />
        </StyledButton>
    );
});

CustomButton.propTypes = {
    children: PropTypes.node,
    ownerState: PropTypes.object.isRequired,
};

const StyledButton = styled('button', { shouldForwardProp: () => true })(
    ({ theme }) => `
    position: relative;
    font-family: 'IBM Plex Sans', sans-serif;
    font-size: 0.875rem;
    box-sizing: border-box;
    min-width: 320px;
    padding: 8px 12px;
    border-radius: 8px;
    text-align: left;
    line-height: 1.5;
    background: ${theme.palette.mode === 'dark' ? grey[900] : '#fff'};
    border: 1px solid ${theme.palette.mode === 'dark' ? grey[700] : grey[200]};
    color: ${theme.palette.mode === 'dark' ? grey[300] : grey[900]};
    box-shadow: 0px 2px 4px ${theme.palette.mode === 'dark' ? 'rgba(0,0,0, 0.5)' : 'rgba(0,0,0, 0.05)'
        };
  
    transition-property: all;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 120ms;
  
    &:hover {
      background: ${theme.palette.mode === 'dark' ? grey[800] : grey[50]};
      border-color: ${theme.palette.mode === 'dark' ? grey[600] : grey[300]};
    }
  
    &.${selectClasses.focusVisible} {
      outline: 0;
      border-color: ${blue[400]};
      box-shadow: 0 0 0 3px ${theme.palette.mode === 'dark' ? blue[700] : blue[200]};
    }
  
    & > svg {
      font-size: 1rem;
      position: absolute;
      height: 100%;
      top: 0;
      right: 10px;
    }
    `,
);