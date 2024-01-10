import React, { useState } from "react";
import { PayPalScriptProvider, PayPalButtons } from "@paypal/react-paypal-js";
import { Box, Button, Container, Typography, Collapse, Alert, Grid } from "@mui/material";
import { Input as BaseInput } from '@mui/base/Input';
import { styled } from '@mui/system';
import UnfoldMoreRoundedIcon from '@mui/icons-material/UnfoldMoreRounded';
import PropTypes from 'prop-types';
import { Select as BaseSelect, selectClasses } from '@mui/base/Select';
import { useSwitch } from '@mui/base/useSwitch';
import clsx from 'clsx';
import { useContext } from "react";
import { IdContext } from "../App.jsx";
import { useNavigate } from "react-router-dom";

// Renders errors or successfull transactions on the screen.
function Message({ content }) {
    return <p>{content}</p>;
}
const CustomInput = React.forwardRef(function CustomInput(props, ref) {
    return <BaseInput slots={{ input: InputElement }} {...props} ref={ref} />;
});

function BasicSwitch(props) {
    const { getInputProps, checked, disabled, focusVisible } = useSwitch(props);

    const stateClasses = {
        'Switch-checked': checked,
        'Switch-disabled': disabled,
        'Switch-focusVisible': focusVisible,
    };

    return (
        <BasicSwitchRoot className={clsx(stateClasses)}>
            <BasicSwitchThumb className={clsx(stateClasses)} />
            <BasicSwitchInput {...getInputProps()} aria-label="Demo switch" />
        </BasicSwitchRoot>
    );
}


function Subscriptions() {
    const initialOptions = {
        "client-id": "test",
        "enable-funding": "",
        "disable-funding": "paylater,venmo,card",
        "data-sdk-integration-source": "integrationbuilder_sc",
    };
    const [message, setMessage] = useState("");
    const [show, setShow] = useState(false);
    const id = useContext(IdContext);
    const [error, setError] = React.useState("");
    const navigate = useNavigate();
    const [cardNumber, setCardNumber] = useState("");
    const [ccv, setCcv] = useState("");
    const [cardHolderName, setCardHolderName] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

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


    const handleSwitchChange = () => {
        setShow(!show);
        setIsButtonDisabled(true);
        setCardNumber("");
        setCcv("");
        setCardHolderName("");
        setPostalCode("");
        setError("");
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
                            Upravitelj pretplatom
                        </Typography>
                        <Typography variant="h4" mb={1}>
                            Pretplata <BasicSwitch onChange={handleSwitchChange} />
                        </Typography>
                        {show ? (
                            <div>
                                <Typography variant="h5" mb={1} >
                                    Plati putem PayPal-a
                                </Typography>
                                <div className="App">
                                    <PayPalScriptProvider options={initialOptions}>
                                        <PayPalButtons
                                            style={{
                                                shape: "rect",
                                                layout: "vertical",
                                            }}
                                            onClick={handlePayPal}
                                        />
                                    </PayPalScriptProvider>
                                    <Message content={message} />
                                </div>
                                <Typography variant="h5" mb={1}>
                                    ili kartičnim plaćanjem
                                </Typography>
                                <CustomInput aria-label="Demo input" placeholder="Broj kartice" onChange={handleCardNumberChange} />
                                <CustomInput aria-label="Demo input" placeholder="CCV" onChange={handleCcvChange} />
                                <CustomInput aria-label="Demo input" placeholder="Ime na kartici" onChange={handleCardHolderNameChange} />
                                <CustomInput aria-label="Demo input" placeholder="Poštanski broj" onChange={handlePostalCodeChange} />
                                <Button
                                    variant="contained"
                                    size="large"
                                    fullWidth
                                    onClick={pretplati}
                                    disabled={isButtonDisabled}
                                >
                                    Pretplati se
                                </Button>
                                <Collapse in={error !== ""}>
                                    <Alert severity="error">{error}</Alert>
                                </Collapse>
                            </div>
                        ) : null}
                        <Button
                            variant="contained"
                            size="large"
                            fullWidth
                            onClick={povratakNaProfil}
                            sx={{
                                backgroundColor: "#e0e0e0", // Adjust the color to make it less prominent
                                '&:hover': {
                                    backgroundColor: "#d0d0d0", // Adjust the hover color

                                },
                                margin: '10px',
                            }}
                        >
                            Povratak na profil
                        </Button>

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

const BasicSwitchRoot = styled('span')(
    ({ theme }) => `
    font-size: 0;
    position: relative;
    display: inline-block;
    width: 38px;
    height: 24px;
    margin: 10px;
    cursor: pointer;
    background: ${theme.palette.mode === 'dark' ? grey[900] : grey[50]};
    border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
    border-radius: 24px;
    box-shadow: inset 0px 1px 1px ${theme.palette.mode === 'dark' ? 'rgba(0, 0, 0, 0.5)' : 'rgba(0, 0, 0, 0.05)'
        };
  
    &:hover {
      background: ${theme.palette.mode === 'dark' ? grey[800] : grey[100]};
    }
      
    &.Switch-focusVisible {
      box-shadow: 0 0 0 3px ${theme.palette.mode === 'dark' ? blue[700] : blue[200]};
    }
  
    &.Switch-disabled {
      opacity: 0.4;
      cursor: not-allowed;
    }
  
    &.Switch-checked {
      border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
      background: ${blue[500]};
      box-shadow: inset 0px 1px 1px ${theme.palette.mode === 'dark' ? 'rgba(0, 0, 0, 0.2)' : 'rgba(0, 0, 0, 0.05)'
        };
      &.Switch-focusVisible {
        box-shadow: 0 0 0 3px ${theme.palette.mode === 'dark' ? blue[700] : blue[200]};
      }
    }
    
    `,
);

const BasicSwitchInput = styled('input')`
    cursor: inherit;
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    opacity: 0;
    z-index: 1;
    margin: 0;
  `;

const BasicSwitchThumb = styled('span')(
    ({ theme }) => `
    display: block;
    width: 16px;
    height: 16px;
    top: 3px;
    left: 2px;
    border-radius: 16px;
    background-color: #fff;
    border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
    position: relative;
    transition-property: all;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 120ms;
    box-shadow: 0px 1px 2px
      ${theme.palette.mode === 'dark' ? 'rgba(0, 0, 0, 0.25)' : 'rgba(0, 0, 0, 0.1)'};
  
    &.Switch-checked {
      left: 17px;
      background-color: #fff;
      border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
    }
  `,
);